
import { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

export default function SimulationResult() {
  const navigate = useNavigate();
  const location = useLocation();
  const { image, selectedClothes } = location.state || {};
  const [showDetails, setShowDetails] = useState(false);

  const simulationDetails = {
    accuracy: 96,
    fitScore: 94,
    styleScore: 92,
    colorHarmony: 95
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-pink-50 to-blue-50 pb-24">
      {/* Header */}
      <div className="fixed top-0 left-0 right-0 bg-white/80 backdrop-blur-md z-50 px-5 py-4 shadow-sm">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <button 
              onClick={() => navigate('/try-on')}
              className="w-9 h-9 flex items-center justify-center rounded-full hover:bg-gray-100 transition-colors"
            >
              <i className="ri-arrow-left-line text-xl text-gray-700"></i>
            </button>
            <h1 className="text-lg font-bold text-gray-800">Deneme Sonucu</h1>
          </div>
          <button 
            onClick={() => setShowDetails(!showDetails)}
            className="w-9 h-9 flex items-center justify-center rounded-full hover:bg-gray-100 transition-colors"
          >
            <i className="ri-information-line text-xl text-gray-700"></i>
          </button>
        </div>
      </div>

      <div className="pt-20 px-5">
        {/* Result Image */}
        <div className="relative mb-6">
          <div className="bg-white rounded-3xl overflow-hidden shadow-xl">
            <img 
              src={image} 
              alt="Result" 
              className="w-full h-96 object-cover object-top"
            />
            <div className="absolute top-4 left-4 right-4 flex items-center justify-between">
              <div className="bg-green-500 text-white rounded-full px-3 py-1.5 flex items-center gap-1.5 shadow-lg">
                <i className="ri-check-double-line text-sm"></i>
                <span className="text-xs font-semibold">Simülasyon Tamamlandı</span>
              </div>
              <button className="w-9 h-9 bg-white/90 backdrop-blur-sm rounded-full flex items-center justify-center shadow-lg">
                <i className="ri-download-line text-gray-700"></i>
              </button>
            </div>
          </div>
        </div>

        {/* Scores */}
        <div className="bg-white rounded-3xl p-5 shadow-sm mb-6">
          <h3 className="font-bold text-gray-800 mb-4 flex items-center gap-2">
            <i className="ri-star-line text-purple-600"></i>
            Uyum Skorları
          </h3>
          <div className="space-y-4">
            {[
              { label: 'Doğruluk', value: simulationDetails.accuracy, icon: 'ri-focus-line', color: 'purple' },
              { label: 'Uyum Skoru', value: simulationDetails.fitScore, icon: 'ri-ruler-line', color: 'blue' },
              { label: 'Stil Skoru', value: simulationDetails.styleScore, icon: 'ri-palette-line', color: 'pink' },
              { label: 'Renk Uyumu', value: simulationDetails.colorHarmony, icon: 'ri-contrast-2-line', color: 'green' }
            ].map((score, index) => (
              <div key={index}>
                <div className="flex items-center justify-between mb-2">
                  <div className="flex items-center gap-2">
                    <i className={`${score.icon} text-${score.color}-600`}></i>
                    <span className="text-sm font-semibold text-gray-700">{score.label}</span>
                  </div>
                  <span className="text-lg font-bold text-gray-800">{score.value}%</span>
                </div>
                <div className="relative h-2 bg-gray-100 rounded-full overflow-hidden">
                  <div 
                    className={`absolute left-0 top-0 h-full bg-gradient-to-r from-${score.color}-400 to-${score.color}-600 rounded-full transition-all duration-1000`}
                    style={{ width: `${score.value}%` }}
                  ></div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Selected Clothes */}
        <div className="bg-white rounded-3xl p-5 shadow-sm mb-6">
          <h3 className="font-bold text-gray-800 mb-4 flex items-center gap-2">
            <i className="ri-shirt-line text-purple-600"></i>
            Denenen Kıyafetler
          </h3>
          <div className="grid grid-cols-3 gap-3">
            {selectedClothes?.map((item: any) => (
              <div key={item.id} className="bg-gray-50 rounded-2xl overflow-hidden">
                <img 
                  src={item.image} 
                  alt={item.name}
                  className="w-full h-24 object-cover object-top"
                />
                <div className="p-2">
                  <p className="text-xs font-semibold text-gray-700 truncate">{item.name}</p>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* AI Analysis */}
        <div className="bg-gradient-to-r from-purple-500 to-pink-500 rounded-3xl p-5 text-white shadow-lg mb-6">
          <div className="flex items-start gap-3 mb-4">
            <div className="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center flex-shrink-0">
              <i className="ri-robot-line text-xl"></i>
            </div>
            <div>
              <h4 className="font-semibold mb-1">Yapay Zeka Analizi</h4>
              <p className="text-xs text-white/90 leading-relaxed">
                Seçtiğiniz kıyafetler vücut yapınıza çok uygun! Renk uyumu mükemmel ve stil tercihiniz harika.
              </p>
            </div>
          </div>
          <div className="grid grid-cols-2 gap-3">
            <div className="bg-white/10 rounded-xl p-3">
              <i className="ri-thumb-up-line text-2xl mb-1"></i>
              <p className="text-xs font-semibold">Vücut Uyumu</p>
              <p className="text-xs text-white/80">Mükemmel</p>
            </div>
            <div className="bg-white/10 rounded-xl p-3">
              <i className="ri-palette-line text-2xl mb-1"></i>
              <p className="text-xs font-semibold">Renk Paleti</p>
              <p className="text-xs text-white/80">Uyumlu</p>
            </div>
          </div>
        </div>

        {/* Action Buttons */}
        <div className="grid grid-cols-2 gap-3">
          <button 
            onClick={() => navigate('/combinations')}
            className="py-4 bg-white rounded-2xl font-semibold text-sm shadow-sm hover:shadow-md transition-all flex items-center justify-center gap-2 text-gray-700"
          >
            <i className="ri-palette-line text-lg"></i>
            Kombin Önerileri
          </button>
          <button 
            onClick={() => navigate('/try-on')}
            className="py-4 bg-gradient-to-r from-purple-600 to-pink-600 text-white rounded-2xl font-semibold text-sm shadow-lg hover:shadow-xl transition-all flex items-center justify-center gap-2"
          >
            <i className="ri-refresh-line text-lg"></i>
            Yeni Deneme
          </button>
        </div>
      </div>
    </div>
  );
}
