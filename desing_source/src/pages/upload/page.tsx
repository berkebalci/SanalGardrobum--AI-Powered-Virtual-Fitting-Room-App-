
import { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Upload() {
  const navigate = useNavigate();
  const fileInputRef = useRef<HTMLInputElement>(null);
  const [uploadedImage, setUploadedImage] = useState<string | null>(null);
  const [measurements, setMeasurements] = useState({
    height: '',
    weight: '',
    size: 'M'
  });
  const [isAnalyzing, setIsAnalyzing] = useState(false);

  const handleFileSelect = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        setUploadedImage(event.target?.result as string);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleAnalyze = () => {
    if (!uploadedImage) return;
    setIsAnalyzing(true);
    setTimeout(() => {
      navigate('/body-analysis', { state: { image: uploadedImage, measurements } });
    }, 2000);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-pink-50 to-blue-50 pb-20">
      {/* Header */}
      <div className="fixed top-0 left-0 right-0 bg-white/80 backdrop-blur-md z-50 px-5 py-4 shadow-sm">
        <div className="flex items-center gap-3">
          <button 
            onClick={() => navigate('/')}
            className="w-9 h-9 flex items-center justify-center rounded-full hover:bg-gray-100 transition-colors"
          >
            <i className="ri-arrow-left-line text-xl text-gray-700"></i>
          </button>
          <h1 className="text-lg font-bold text-gray-800">Fotoğraf Yükleme</h1>
        </div>
      </div>

      <div className="pt-20 px-5">
        {/* Upload Area */}
        <div className="mb-6">
          <h2 className="text-xl font-bold text-gray-800 mb-2">Fotoğrafınızı Yükleyin</h2>
          <p className="text-sm text-gray-600 mb-4">En iyi sonuç için boydan, düz bir pozda çekilmiş fotoğraf kullanın</p>
          
          <div 
            onClick={() => fileInputRef.current?.click()}
            className="relative bg-white rounded-3xl overflow-hidden shadow-lg cursor-pointer hover:shadow-xl transition-shadow"
          >
            {uploadedImage ? (
              <div className="relative">
                <img 
                  src={uploadedImage} 
                  alt="Uploaded" 
                  className="w-full h-96 object-cover object-top"
                />
                <div className="absolute top-4 right-4 bg-white/90 backdrop-blur-sm rounded-full px-3 py-1.5 flex items-center gap-1.5">
                  <i className="ri-check-line text-green-600 text-sm"></i>
                  <span className="text-xs font-semibold text-gray-700">Yüklendi</span>
                </div>
                <button 
                  onClick={(e) => {
                    e.stopPropagation();
                    setUploadedImage(null);
                  }}
                  className="absolute bottom-4 right-4 w-10 h-10 bg-red-500 rounded-full flex items-center justify-center shadow-lg"
                >
                  <i className="ri-delete-bin-line text-white text-lg"></i>
                </button>
              </div>
            ) : (
              <div className="h-96 flex flex-col items-center justify-center p-8">
                <div className="w-20 h-20 rounded-full bg-gradient-to-br from-purple-400 to-pink-400 flex items-center justify-center mb-4">
                  <i className="ri-camera-line text-3xl text-white"></i>
                </div>
                <h3 className="text-lg font-semibold text-gray-800 mb-2">Fotoğraf Seçin</h3>
                <p className="text-sm text-gray-500 text-center mb-4">
                  Galeriden seçin veya<br/>yeni fotoğraf çekin
                </p>
                <div className="flex gap-2">
                  <div className="px-3 py-1.5 bg-purple-50 rounded-full">
                    <span className="text-xs text-purple-600 font-medium">JPG</span>
                  </div>
                  <div className="px-3 py-1.5 bg-pink-50 rounded-full">
                    <span className="text-xs text-pink-600 font-medium">PNG</span>
                  </div>
                </div>
              </div>
            )}
          </div>
          <input 
            ref={fileInputRef}
            type="file" 
            accept="image/*" 
            onChange={handleFileSelect}
            className="hidden"
          />
        </div>

        {/* Tips */}
        <div className="bg-blue-50 rounded-2xl p-4 mb-6">
          <div className="flex gap-3">
            <div className="w-8 h-8 rounded-full bg-blue-500 flex items-center justify-center flex-shrink-0">
              <i className="ri-information-line text-white text-lg"></i>
            </div>
            <div>
              <h3 className="font-semibold text-gray-800 text-sm mb-2">İpuçları</h3>
              <ul className="space-y-1.5 text-xs text-gray-600">
                <li className="flex items-start gap-2">
                  <i className="ri-checkbox-circle-fill text-blue-500 text-sm mt-0.5"></i>
                  <span>Düz, aydınlık bir ortamda çekin</span>
                </li>
                <li className="flex items-start gap-2">
                  <i className="ri-checkbox-circle-fill text-blue-500 text-sm mt-0.5"></i>
                  <span>Vücudunuz tam görünür olsun</span>
                </li>
                <li className="flex items-start gap-2">
                  <i className="ri-checkbox-circle-fill text-blue-500 text-sm mt-0.5"></i>
                  <span>Dar kıyafetler tercih edin</span>
                </li>
              </ul>
            </div>
          </div>
        </div>

        {/* Measurements (Optional) */}
        <div className="bg-white rounded-3xl p-5 shadow-sm mb-6">
          <h3 className="font-bold text-gray-800 mb-1">Vücut Ölçüleri</h3>
          <p className="text-xs text-gray-500 mb-4">İsteğe bağlı - Daha doğru sonuçlar için</p>
          
          <div className="space-y-4">
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Boy (cm)</label>
              <input 
                type="number"
                value={measurements.height}
                onChange={(e) => setMeasurements({...measurements, height: e.target.value})}
                placeholder="Örn: 170"
                className="w-full px-4 py-3 bg-gray-50 rounded-xl text-sm border-none focus:ring-2 focus:ring-purple-500 outline-none"
              />
            </div>
            
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Kilo (kg)</label>
              <input 
                type="number"
                value={measurements.weight}
                onChange={(e) => setMeasurements({...measurements, weight: e.target.value})}
                placeholder="Örn: 65"
                className="w-full px-4 py-3 bg-gray-50 rounded-xl text-sm border-none focus:ring-2 focus:ring-purple-500 outline-none"
              />
            </div>
            
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Beden Tercihi</label>
              <div className="grid grid-cols-5 gap-2">
                {['XS', 'S', 'M', 'L', 'XL'].map((size) => (
                  <button
                    key={size}
                    onClick={() => setMeasurements({...measurements, size})}
                    className={`py-3 rounded-xl text-sm font-semibold transition-all ${
                      measurements.size === size
                        ? 'bg-gradient-to-br from-purple-500 to-pink-500 text-white shadow-md'
                        : 'bg-gray-50 text-gray-600 hover:bg-gray-100'
                    }`}
                  >
                    {size}
                  </button>
                ))}
              </div>
            </div>
          </div>
        </div>

        {/* Analyze Button */}
        <button 
          onClick={handleAnalyze}
          disabled={!uploadedImage || isAnalyzing}
          className={`w-full py-4 rounded-2xl font-semibold text-base shadow-lg transition-all flex items-center justify-center gap-2 ${
            uploadedImage && !isAnalyzing
              ? 'bg-gradient-to-r from-purple-600 to-pink-600 text-white hover:shadow-xl'
              : 'bg-gray-200 text-gray-400 cursor-not-allowed'
          }`}
        >
          {isAnalyzing ? (
            <>
              <i className="ri-loader-4-line text-xl animate-spin"></i>
              Analiz Ediliyor...
            </>
          ) : (
            <>
              <i className="ri-scan-line text-xl"></i>
              Analizi Başlat
            </>
          )}
        </button>
      </div>
    </div>
  );
}
